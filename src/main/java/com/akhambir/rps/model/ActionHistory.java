package com.akhambir.rps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "ACTIONS")
public class ActionHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "PARTICIPANT")
  private Participant participant;
  @Enumerated(EnumType.STRING)
  @Column(name = "ACTION")
  private Action action;
  @Enumerated(EnumType.STRING)
  @Column(name = "GAME_RESULT")
  private GameResult gameResult;
  @ManyToOne
  @JoinColumn(name = "FK_GAME_RESULT_ID")
  private GameHistory gameHistory;
}
