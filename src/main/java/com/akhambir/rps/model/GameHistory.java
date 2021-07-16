package com.akhambir.rps.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "GAME_RESULTS")
public class GameHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "CREATED_AT")
  private java.sql.Timestamp createdAt;
  @OneToMany(mappedBy = "gameResult")
  private List<ActionHistory> actionHistory = new ArrayList<>();
}
