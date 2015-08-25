package com.ZLHW.bussiness.model;

import java.util.Date;

public class TrainDetail
{
  private String times;
  private Date trainDate;
  private Byte trainType;
  private Integer trainScore;
  private Training training;

  public String getTimes()
  {
    return this.times; }

  public void setTimes(String times) {
    this.times = times; }

  public Date getTrainDate() {
    return this.trainDate; }

  public void setTrainDate(Date trainDate) {
    this.trainDate = trainDate; }

  public Byte getTrainType() {
    return this.trainType; }

  public void setTrainType(Byte trainType) {
    this.trainType = trainType; }

  public Integer getTrainScore() {
    return this.trainScore; }

  public void setTrainScore(Integer trainScore) {
    this.trainScore = trainScore; }

  public Training getTraining() {
    return this.training; }

  public void setTraining(Training training) {
    this.training = training;
  }
}