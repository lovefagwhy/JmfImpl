package com.jmf.impl.dao;

import java.util.List;

import com.jmf.impl.entity.JmfConfig;

public interface JmfConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table JMF_CONFIG
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String jmfConfigId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table JMF_CONFIG
     *
     * @mbg.generated
     */
    int insert(JmfConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table JMF_CONFIG
     *
     * @mbg.generated
     */
    JmfConfig selectByPrimaryKey(String jmfConfigId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table JMF_CONFIG
     *
     * @mbg.generated
     */
    List<JmfConfig> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table JMF_CONFIG
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(JmfConfig record);
}