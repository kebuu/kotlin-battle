angular.module('app').component('gameCreator', {
    templateUrl: '/template/gameCreator.html',
    controller: function($http) {
        
        this.$onInit = function() {
            this.gameLevel = "LEVEL_0";
        };

        this.createGame = function(gameLevel) {
            console.log(gameLevel);
            $http.get("/games/new", {params: {gameLevel: gameLevel}});
        };
    },
    bindings: {

    }
});