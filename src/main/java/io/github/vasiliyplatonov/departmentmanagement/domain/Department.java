package io.github.vasiliyplatonov.departmentmanagement.domain;

public class Department {
    private int id;
    private String name;
    private Integer parentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Department() {
    }

    public Department(int id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Department(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
