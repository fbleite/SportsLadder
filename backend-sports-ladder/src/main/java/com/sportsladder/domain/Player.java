package com.sportsladder.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Felipe Leite on 7/1/2017.
 */
@Entity
public class Player implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name="name", nullable = false)
    String name;

    @Column(name="rank")
    Integer rank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }


    @Override
    public int compareTo(Object o) {
        Player otherPlayer = (Player) o;
        if (this.getRank() == otherPlayer.getRank()) return 0;
        if (this.getRank() == null) return 1;
        if (otherPlayer.getRank() == null) return -1;
        return this.getRank().compareTo(((Player) o).getRank());
    }

    @Override
    public boolean equals(Object o) {
        Player otherPlayer = (Player) o;
        if (this.getId() == otherPlayer.getId() &&
                this.getRank() == otherPlayer.getRank() &&
                this.getName().equals(otherPlayer.getName())
                ) {
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "id: " + this.getId() + ", name: " +this.getName() + ", rank: " + this.getRank();
    }
}
