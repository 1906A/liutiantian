package com.leyou.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name="tb_spec_group")
public class SpecGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private String name;
    private List<SpecParam> paramList;


    public List<SpecParam> getParamList() {
        return paramList;
    }

    public void setParamList(List<SpecParam> paramList) {
        this.paramList = paramList;
    }

    public SpecGroup(Long id, Long cid, String name) {
        this.id = id;
        this.cid = cid;
        this.name = name;
    }

    public SpecGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
