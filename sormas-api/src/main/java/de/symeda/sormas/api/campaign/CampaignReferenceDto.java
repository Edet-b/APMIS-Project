/*
 * ******************************************************************************
 * * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * * Copyright © 2016-2020 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 * *
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as published by
 * * the Free Software Foundation, either version 3 of the License, or
 * * (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public License
 * * along with this program. If not, see <https://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

package de.symeda.sormas.api.campaign;

import java.util.Date;
import java.util.HashMap;

import de.symeda.sormas.api.ReferenceDto;

public class CampaignReferenceDto extends ReferenceDto {
	
	private String campaignYear;
	
	private Date startDate;

	public CampaignReferenceDto() {
	}

	public CampaignReferenceDto(String uuid) {
		setUuid(uuid);
	}

	public CampaignReferenceDto(String uuid, String caption) {
		setUuid(uuid);
		setCaption(caption);
	}
	
	public CampaignReferenceDto(String uuid, String caption, String campaignYear) {
		setUuid(uuid);
		setCaption(caption);
		this.campaignYear = campaignYear;
	}
	
	public CampaignReferenceDto(String uuid, String caption, String campaignYear, Date startDate) {
		setUuid(uuid);
		setCaption(caption);
		this.campaignYear = campaignYear;
		this.startDate = startDate;
	}

	public String getCampaignYear() {
		return campaignYear;
	}

	public void setCampaignYear(String campaignYear) {
		this.campaignYear = campaignYear;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}