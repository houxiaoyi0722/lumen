package com.sang.flowable.dto;


/**
 * REST representation of the breakpoint
 *
 * @author martin.grofcik
 */
public final class BreakpointRepresentation extends AbstractRepresentation {

    protected String activityId;
    protected String processDefinitionId;

    public BreakpointRepresentation(String processDefinitionId, String activityId) {
        this.processDefinitionId = processDefinitionId;
        this.activityId = activityId;
    }

    public BreakpointRepresentation() {
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BreakpointRepresentation that = (BreakpointRepresentation) o;

        if (!getActivityId().equals(that.getActivityId())) {
            return false;
        }

        if (processDefinitionId == null && that.getProcessDefinitionId() == null) {
            return true;
        }

        return getProcessDefinitionId().equals(that.getProcessDefinitionId());
    }

    @Override
    public int hashCode() {
        int result = getActivityId().hashCode();
        result = 31 * result + getProcessDefinitionId().hashCode();
        return result;
    }
}
