import {Component, OnInit, ElementRef} from '@angular/core';
import {Player} from "./player.model";
import {trigger, transition, style, animate} from "@angular/animations";

@Component({
  selector: 'app-players',
  templateUrl: 'players.component.html',
  styleUrls: ['players.component.css']
})

export class PlayersComponent implements OnInit {
  newPlayerPending:boolean = true;
  newPlayerName:string = "";
  newPlayerRank:number = null;
  players:Player[] = [new Player("Mr Milk", 1), new Player("Mr Shake", 2)];

  errors = [];

  constructor(private elRef: ElementRef) { }
  ngOnInit() {
  }

  onAddPlayer() {
    this.newPlayerPending = false;
  }

  onSavePlayer() {
    this.errors = [];
    this.validateNewPlayer(this.newPlayerName, this.newPlayerRank);
    if (this.errors.length == 0) {
      this.players.push(new Player(this.newPlayerName, this.newPlayerRank));
      this.clearPendingPlayer();
    }
  }

  /**
   * I don't have internet and can't search how to type the return of functions :|
   * Sorry for the non pure functions.
   * @param name
   * @param rank
   */
  validateNewPlayer(name:string, rank:number) {
    if (name == "") {
      this.errors.push("Name cannot be empty");
    }
    // Should we allow unranked people on the ladder or should everyone have a rank?
    if (rank == null) {
      this.errors.push("Rank cannot be empty");
    }
  }

  onCancelAdd() {
    this.clearPendingPlayer();
  }

  clearPendingPlayer() {
    this.newPlayerPending = true;
    this.newPlayerRank=null;
    this.newPlayerName = "";
    this.errors = [];
  }

  /**
   * TODO: We probably don't need these funtions below, a data binding should do the job.
   * @param event
   */
  onUpdatePlayerName(event:Event) {
    this.newPlayerName = (<HTMLInputElement> event.target).value;
  }

  onUpdatePlayerRank(event:Event) {
    this.newPlayerRank = Number.parseFloat((<HTMLInputElement> event.target).value);
  }

}
