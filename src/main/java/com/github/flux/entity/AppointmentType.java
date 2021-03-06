package com.github.flux.entity;

import java.io.Serializable;

public class AppointmentType implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment_type.type_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private Long typeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment_type.name
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column appointment_type.template
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    private String template;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment_type.type_id
     *
     * @return the value of appointment_type.type_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment_type.type_id
     *
     * @param typeId the value for appointment_type.type_id
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment_type.name
     *
     * @return the value of appointment_type.name
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment_type.name
     *
     * @param name the value for appointment_type.name
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column appointment_type.template
     *
     * @return the value of appointment_type.template
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public String getTemplate() {
        return template;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column appointment_type.template
     *
     * @param template the value for appointment_type.template
     *
     * @mbggenerated Thu Aug 13 21:52:06 CST 2015
     */
    public void setTemplate(String template) {
        this.template = template;
    }

	@Override
	public String toString() {
		return "AppointmentType [typeId=" + typeId + ", name=" + name + ", template=" + template + "]";
	}
    
    
}