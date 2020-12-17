package com.zty.springboot01login.Pojo;

import java.util.ArrayList;
import java.util.List;

public class CourseEnvExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CourseEnvExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andEnvIdIsNull() {
            addCriterion("env_id is null");
            return (Criteria) this;
        }

        public Criteria andEnvIdIsNotNull() {
            addCriterion("env_id is not null");
            return (Criteria) this;
        }

        public Criteria andEnvIdEqualTo(String value) {
            addCriterion("env_id =", value, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdNotEqualTo(String value) {
            addCriterion("env_id <>", value, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdGreaterThan(String value) {
            addCriterion("env_id >", value, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdGreaterThanOrEqualTo(String value) {
            addCriterion("env_id >=", value, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdLessThan(String value) {
            addCriterion("env_id <", value, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdLessThanOrEqualTo(String value) {
            addCriterion("env_id <=", value, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdLike(String value) {
            addCriterion("env_id like", value, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdNotLike(String value) {
            addCriterion("env_id not like", value, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdIn(List<String> values) {
            addCriterion("env_id in", values, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdNotIn(List<String> values) {
            addCriterion("env_id not in", values, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdBetween(String value1, String value2) {
            addCriterion("env_id between", value1, value2, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvIdNotBetween(String value1, String value2) {
            addCriterion("env_id not between", value1, value2, "envId");
            return (Criteria) this;
        }

        public Criteria andEnvNameIsNull() {
            addCriterion("env_name is null");
            return (Criteria) this;
        }

        public Criteria andEnvNameIsNotNull() {
            addCriterion("env_name is not null");
            return (Criteria) this;
        }

        public Criteria andEnvNameEqualTo(String value) {
            addCriterion("env_name =", value, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameNotEqualTo(String value) {
            addCriterion("env_name <>", value, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameGreaterThan(String value) {
            addCriterion("env_name >", value, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameGreaterThanOrEqualTo(String value) {
            addCriterion("env_name >=", value, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameLessThan(String value) {
            addCriterion("env_name <", value, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameLessThanOrEqualTo(String value) {
            addCriterion("env_name <=", value, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameLike(String value) {
            addCriterion("env_name like", value, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameNotLike(String value) {
            addCriterion("env_name not like", value, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameIn(List<String> values) {
            addCriterion("env_name in", values, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameNotIn(List<String> values) {
            addCriterion("env_name not in", values, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameBetween(String value1, String value2) {
            addCriterion("env_name between", value1, value2, "envName");
            return (Criteria) this;
        }

        public Criteria andEnvNameNotBetween(String value1, String value2) {
            addCriterion("env_name not between", value1, value2, "envName");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andNodeNameIsNull() {
            addCriterion("node_name is null");
            return (Criteria) this;
        }

        public Criteria andNodeNameIsNotNull() {
            addCriterion("node_name is not null");
            return (Criteria) this;
        }

        public Criteria andNodeNameEqualTo(String value) {
            addCriterion("node_name =", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotEqualTo(String value) {
            addCriterion("node_name <>", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameGreaterThan(String value) {
            addCriterion("node_name >", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameGreaterThanOrEqualTo(String value) {
            addCriterion("node_name >=", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameLessThan(String value) {
            addCriterion("node_name <", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameLessThanOrEqualTo(String value) {
            addCriterion("node_name <=", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameLike(String value) {
            addCriterion("node_name like", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotLike(String value) {
            addCriterion("node_name not like", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameIn(List<String> values) {
            addCriterion("node_name in", values, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotIn(List<String> values) {
            addCriterion("node_name not in", values, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameBetween(String value1, String value2) {
            addCriterion("node_name between", value1, value2, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotBetween(String value1, String value2) {
            addCriterion("node_name not between", value1, value2, "nodeName");
            return (Criteria) this;
        }

        public Criteria andCpuIsNull() {
            addCriterion("cpu is null");
            return (Criteria) this;
        }

        public Criteria andCpuIsNotNull() {
            addCriterion("cpu is not null");
            return (Criteria) this;
        }

        public Criteria andCpuEqualTo(Integer value) {
            addCriterion("cpu =", value, "cpu");
            return (Criteria) this;
        }

        public Criteria andCpuNotEqualTo(Integer value) {
            addCriterion("cpu <>", value, "cpu");
            return (Criteria) this;
        }

        public Criteria andCpuGreaterThan(Integer value) {
            addCriterion("cpu >", value, "cpu");
            return (Criteria) this;
        }

        public Criteria andCpuGreaterThanOrEqualTo(Integer value) {
            addCriterion("cpu >=", value, "cpu");
            return (Criteria) this;
        }

        public Criteria andCpuLessThan(Integer value) {
            addCriterion("cpu <", value, "cpu");
            return (Criteria) this;
        }

        public Criteria andCpuLessThanOrEqualTo(Integer value) {
            addCriterion("cpu <=", value, "cpu");
            return (Criteria) this;
        }

        public Criteria andCpuIn(List<Integer> values) {
            addCriterion("cpu in", values, "cpu");
            return (Criteria) this;
        }

        public Criteria andCpuNotIn(List<Integer> values) {
            addCriterion("cpu not in", values, "cpu");
            return (Criteria) this;
        }

        public Criteria andCpuBetween(Integer value1, Integer value2) {
            addCriterion("cpu between", value1, value2, "cpu");
            return (Criteria) this;
        }

        public Criteria andCpuNotBetween(Integer value1, Integer value2) {
            addCriterion("cpu not between", value1, value2, "cpu");
            return (Criteria) this;
        }

        public Criteria andMemsizeIsNull() {
            addCriterion("memsize is null");
            return (Criteria) this;
        }

        public Criteria andMemsizeIsNotNull() {
            addCriterion("memsize is not null");
            return (Criteria) this;
        }

        public Criteria andMemsizeEqualTo(Integer value) {
            addCriterion("memsize =", value, "memsize");
            return (Criteria) this;
        }

        public Criteria andMemsizeNotEqualTo(Integer value) {
            addCriterion("memsize <>", value, "memsize");
            return (Criteria) this;
        }

        public Criteria andMemsizeGreaterThan(Integer value) {
            addCriterion("memsize >", value, "memsize");
            return (Criteria) this;
        }

        public Criteria andMemsizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("memsize >=", value, "memsize");
            return (Criteria) this;
        }

        public Criteria andMemsizeLessThan(Integer value) {
            addCriterion("memsize <", value, "memsize");
            return (Criteria) this;
        }

        public Criteria andMemsizeLessThanOrEqualTo(Integer value) {
            addCriterion("memsize <=", value, "memsize");
            return (Criteria) this;
        }

        public Criteria andMemsizeIn(List<Integer> values) {
            addCriterion("memsize in", values, "memsize");
            return (Criteria) this;
        }

        public Criteria andMemsizeNotIn(List<Integer> values) {
            addCriterion("memsize not in", values, "memsize");
            return (Criteria) this;
        }

        public Criteria andMemsizeBetween(Integer value1, Integer value2) {
            addCriterion("memsize between", value1, value2, "memsize");
            return (Criteria) this;
        }

        public Criteria andMemsizeNotBetween(Integer value1, Integer value2) {
            addCriterion("memsize not between", value1, value2, "memsize");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("create_time not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("creator_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("creator_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(String value) {
            addCriterion("creator_id =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(String value) {
            addCriterion("creator_id <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(String value) {
            addCriterion("creator_id >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(String value) {
            addCriterion("creator_id >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(String value) {
            addCriterion("creator_id <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(String value) {
            addCriterion("creator_id <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLike(String value) {
            addCriterion("creator_id like", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotLike(String value) {
            addCriterion("creator_id not like", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<String> values) {
            addCriterion("creator_id in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<String> values) {
            addCriterion("creator_id not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(String value1, String value2) {
            addCriterion("creator_id between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(String value1, String value2) {
            addCriterion("creator_id not between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andImageIdIsNull() {
            addCriterion("image_id is null");
            return (Criteria) this;
        }

        public Criteria andImageIdIsNotNull() {
            addCriterion("image_id is not null");
            return (Criteria) this;
        }

        public Criteria andImageIdEqualTo(String value) {
            addCriterion("image_id =", value, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdNotEqualTo(String value) {
            addCriterion("image_id <>", value, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdGreaterThan(String value) {
            addCriterion("image_id >", value, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdGreaterThanOrEqualTo(String value) {
            addCriterion("image_id >=", value, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdLessThan(String value) {
            addCriterion("image_id <", value, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdLessThanOrEqualTo(String value) {
            addCriterion("image_id <=", value, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdLike(String value) {
            addCriterion("image_id like", value, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdNotLike(String value) {
            addCriterion("image_id not like", value, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdIn(List<String> values) {
            addCriterion("image_id in", values, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdNotIn(List<String> values) {
            addCriterion("image_id not in", values, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdBetween(String value1, String value2) {
            addCriterion("image_id between", value1, value2, "imageId");
            return (Criteria) this;
        }

        public Criteria andImageIdNotBetween(String value1, String value2) {
            addCriterion("image_id not between", value1, value2, "imageId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}