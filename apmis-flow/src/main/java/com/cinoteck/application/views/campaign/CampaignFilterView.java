package com.cinoteck.application.views.campaign;

//import com.cinoteck.application.views.campaign.MonthlyExpense.DailyExpenses;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;

import de.symeda.sormas.api.EntityRelevanceStatus;
import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.campaign.CampaignDto;
import de.symeda.sormas.api.campaign.CampaignReferenceDto;
import de.symeda.sormas.api.i18n.Captions;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.infrastructure.area.AreaReferenceDto;

public class CampaignFilterView extends VerticalLayout {
	Binder<CampaignDto> binder = new BeanValidationBinder<>(CampaignDto.class);

	private final TextField filterr = new TextField();

	private final Button addNewBtnn = new Button();

	private TextField searchField = new TextField();

	private Button displayFilters = new Button(I18nProperties.getCaption(Captions.showFilters));

	public CampaignFilterView() {
		

		displayFilters.getStyle().set("margin-left", "12px");
		displayFilters.getStyle().set("margin-top", "12px");
		displayFilters.setIcon(new Icon(VaadinIcon.SLIDERS));
		

		HorizontalLayout campaignDataFilterLayout = new HorizontalLayout();
		campaignDataFilterLayout.getStyle().set("margin-left", "12px");
		campaignDataFilterLayout.setAlignItems(Alignment.END);
		campaignDataFilterLayout.setVisible(false);
		
		displayFilters.addClickListener(e -> {
			if (!campaignDataFilterLayout.isVisible()) {
				campaignDataFilterLayout.setVisible(true);
				displayFilters.setText(I18nProperties.getCaption(Captions.hideFilters));

			} else {
				campaignDataFilterLayout.setVisible(false);
				displayFilters.setText(I18nProperties.getCaption(Captions.showFilters));
			}

		});

		searchField.setLabel(I18nProperties.getCaption(Captions.campaignSearch));
		searchField.setPlaceholder(I18nProperties.getCaption(Captions.actionSearch));
		searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		searchField.setValueChangeMode(ValueChangeMode.EAGER);
		

		ComboBox<EntityRelevanceStatus> campaignStatus = new ComboBox<EntityRelevanceStatus>();
		campaignStatus.setLabel(I18nProperties.getCaption(Captions.campaignStatus));

		campaignStatus.setItems((EntityRelevanceStatus[]) EntityRelevanceStatus.values());

		Button validateForms = new Button(I18nProperties.getCaption(Captions.campaignValidateForms), new Icon(VaadinIcon.CHECK_CIRCLE));
		Button addNewForm = new Button(I18nProperties.getCaption(Captions.addNewForm), new Icon(VaadinIcon.PLUS_CIRCLE));

		campaignDataFilterLayout.add(searchField, campaignStatus, validateForms, addNewForm);

		add(displayFilters, campaignDataFilterLayout);
	}

}