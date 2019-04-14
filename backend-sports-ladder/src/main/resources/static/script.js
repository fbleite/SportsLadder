function init() {
    getPlayers();
}



window.onload = init;

function getPlayers() {
    fetch('/player/')
        .then(response => response.json())
        .then(players => {
            addPlayersToTables(players);
        });

}


function addPlayersToTables(players) {
    for (let player in players) {
        if (isPlayerRanked(players[player])) {
            addToRankedTable(players[player].name, players[player].rank);
        }
        else {
            addToUnrankedTable(players[player].name);
        }
    }
    function isPlayerRanked(player) {
        return player.rank != null;
    }
}

function addToRankedTable(name, rank) {
    var tableSelectorId = "rank";
    var table = document.getElementById(tableSelectorId);
    var row = table.insertRow(table.rows.length);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    cell1.innerHTML = rank;
    cell2.innerHTML = name;
}

function addToUnrankedTable(name) {
    var tableSelectorId = "unrank";
    var table = document.getElementById(tableSelectorId);
    var row = table.insertRow(table.rows.length);
    var cell1 = row.insertCell(0);
    cell1.innerHTML = name;
}

function addPlayerTextBox() {
    toggleVisibilityPlayerAdding();
}


function toggleVisibilityPlayerAdding() {
    for (var i = 0; i < document.getElementsByClassName("showToSavePlayer").length; i++) {
        toggleIndividual(document.getElementsByClassName("showToSavePlayer")[i]);
    }
    toggleIndividual(document.getElementsByClassName("hideToSavePlayer")[0]);
}


function toggleIndividual(element) {
    if (element.style.display === "none") {
        element.style.display = "block";
      } else {
        element.style.display = "none";
      }
}

function saveNewPlayer() {
    var newPlayerName = document.getElementById("newPlayerName").value;
    if (newPlayerName != "") {
    addToUnrankedTable(newPlayerName);
    toggleVisibilityPlayerAdding();
    fetch ('/player/add/'+ newPlayerName+'/')
        .catch(error => console.log(error));
    document.getElementById("newPlayerName").value = "";
    }
}
