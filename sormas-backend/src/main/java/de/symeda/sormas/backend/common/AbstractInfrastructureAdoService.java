package de.symeda.sormas.backend.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.vladmihalcea.hibernate.type.util.SQLExtractor;

import de.symeda.sormas.api.EntityRelevanceStatus;
import de.symeda.sormas.backend.epidata.EpiData;
import de.symeda.sormas.backend.exposure.Exposure;
import de.symeda.sormas.backend.infrastructure.PopulationData;
import de.symeda.sormas.backend.infrastructure.area.Area;
import de.symeda.sormas.backend.infrastructure.region.Region;
import de.symeda.sormas.backend.util.QueryHelper;

public abstract class AbstractInfrastructureAdoService<ADO extends InfrastructureAdo> extends AdoServiceWithUserFilter<ADO> {

	public AbstractInfrastructureAdoService(Class<ADO> elementClass) {
		super(elementClass);
	}

	public void archive(ADO archiveme) {

		archiveme.setArchived(true);
		em.persist(archiveme);
		em.flush();
	}

	public Predicate createBasicFilter(CriteriaBuilder cb, Root<ADO> root) {
		return cb.isFalse(root.get(InfrastructureAdo.ARCHIVED));
	}

	public List<ADO> getAllActive() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ADO> cq = cb.createQuery(getElementClass());
		Root<ADO> from = cq.from(getElementClass());
		cq.where(createBasicFilter(cb, from));
		cq.orderBy(cb.desc(from.get(AbstractDomainObject.CHANGE_DATE)));

		return em.createQuery(cq).getResultList();
	}

	public List<ADO> getAllActive(String orderProperty, boolean asc) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ADO> cq = cb.createQuery(getElementClass());
		Root<ADO> from = cq.from(getElementClass());
		cq.where(createBasicFilter(cb, from));
		cq.orderBy(asc ? cb.asc(from.get(orderProperty)) : cb.desc(from.get(orderProperty)));
		System.out.println("DDDDDDDDDDDDDDDDDDDDdddddddddddddddddDDDDDDDDDDDDDDDDDDDDDDddd "+ SQLExtractor.from(em.createQuery(cq)));
		return em.createQuery(cq).getResultList();
	}
	
	public <T extends InfrastructureAdo> boolean isUsedInInfrastructureData(String uuid, String adoAttribute, Class<T> targetElementClass) {

		List<String> uuidList = new ArrayList<>();
		uuidList.add(uuid);
		return isUsedInInfrastructureData(uuidList, adoAttribute, targetElementClass);
	}

	public <T extends InfrastructureAdo> boolean isUsedInInfrastructureData(
		Collection<String> uuids,
		String adoAttribute,
		Class<T> targetElementClass) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(targetElementClass);
		Root<T> root = cq.from(targetElementClass);
		Join<T, ADO> join = root.join(adoAttribute);

		cq.where(
			cb.and(
				cb.or(cb.isNull(root.get(InfrastructureAdo.ARCHIVED)), cb.isFalse(root.get(InfrastructureAdo.ARCHIVED))),
				join.get(InfrastructureAdo.UUID).in(uuids)));

		cq.select(join.get(InfrastructureAdo.ID));

		return QueryHelper.getFirstResult(em, cq) != null;
	}

	protected Predicate addRelevancePredicate(CriteriaBuilder cb, Root<?> from, Predicate filter, EntityRelevanceStatus relevanceStatus) {
		if (relevanceStatus != null) {
			if (relevanceStatus == EntityRelevanceStatus.ACTIVE) {
				filter = CriteriaBuilderHelper
					.and(cb, filter, cb.or(cb.equal(from.get(InfrastructureAdo.ARCHIVED), false), cb.isNull(from.get(InfrastructureAdo.ARCHIVED))));
			} else if (relevanceStatus == EntityRelevanceStatus.ARCHIVED) {
				filter = CriteriaBuilderHelper.and(cb, filter, cb.equal(from.get(InfrastructureAdo.ARCHIVED), true));
			}
		}
		return filter;
	}

	// todo remove columnName later and handle this completely here. This is not possible due to #6549 now.
	protected List<ADO> getByExternalId(Long externalId, String columnName, boolean includeArchived) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ADO> cq = cb.createQuery(getElementClass());
		Root<ADO> from = cq.from(getElementClass());

	//	Predicate filter;// = new Predicate;  //CriteriaBuilderHelper.ilikePrecise(cb, from.get(columnName), externalId.toString());
	//	if (!includeArchived) {
	//		filter = cb.and(filter, createBasicFilter(cb, from));
	//	}
		
		//this was changed from externalID
		cq.where(cb.equal(from.get("externalId"), externalId));

		return em.createQuery(cq).getResultList();

	}

	public abstract List<ADO> getByExternalId(Long externalId, boolean includeArchived);
}
