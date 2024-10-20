package fr.casino;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * On doit arriver à retrouver ce DTO depuis l'attribut de {@link DTOCompose}.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public class DTOIsole {
	/**
	 * Some docs.
	 */
	private String documented;

	public String getDocumented() {
		return documented;
	}

	public void setDocumented(String documented) {
		this.documented = documented;
	}
}
