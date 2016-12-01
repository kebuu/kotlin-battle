angular.module('app').component('gameList', {
    templateUrl: '/template/gameList.html',
    controller: function($http, $rootScope) {
        var ctrl = this;
        ctrl.loading = false;
        ctrl.selectedGame = {};

        ctrl.$onInit = function() {
            ctrl.getGames();
        }

        ctrl.getGames = function() {
            ctrl.loading = true;
            $http.get('/games').then((response) => {
                ctrl.games = response.data;
            }).finally(() => {
                ctrl.loading = false;
            });
        }

        ctrl.createNewGame = function() {
            $http.post('/games').then(() => {
                ctrl.getGames();
            });
        }

        ctrl.onGameClicked = function(selectedGame) {
            ctrl.selectedGame = selectedGame;
            $rootScope.$broadcast("game-list:clicked", gameId)
        }
    },
    bindings: {

    }
});