package com.nnacres.assessment.dto;
public class ExecutionTaskResult {
   private Boolean result;
   private Integer marks;
   public ExecutionTaskResult() {
       //default
   }
   public ExecutionTaskResult(Boolean result, Integer marks) {
       this.result = result;
       this.marks = marks;
   }
   public Boolean getResult() {
       return result;
   }
   public void setResult(Boolean result) {
       this.result = result;
   }
   public Integer getMarks() {
       return marks;
   }
   public void setMarks(Integer marks) {
       this.marks = marks;
   }
}