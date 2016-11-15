angular.module('app').component('gameView', {
    templateUrl: '/template/gameView.html',
    controller: function($http, $scope) {
        this.game = {};

        this.$onInit = function() {
            $http.get(`/games/active`).then((response) => {
                this.game = response.data;
            });

            this.connect();
        }

        this.onStartPressed = function() {
            $http.get(`/games/start`);
        }

        this.connect = function() {
            var socket = new SockJS('/kotlin-battle');
            let stompClient = Stomp.over(socket);

            stompClient.connect({}, frame =>  {
                stompClient.subscribe('/topic/active-game', data =>  {
                    $scope.$apply(() => {
                        this.game = JSON.parse(data.body);
                        console.log(this.game);
                    })
                });
            });
        };

        this.isUserAdmin = function() {
            return this.user.authorities.some(authority => authority.authority == "ROLE_ADMIN");
        };

        this.isUserRegistered = function() {
            return this.game.gamers.some(gamer => gamer.pseudo == this.user.email);
        };

        this.register = function() {
            $http.get("/games/register").catch(() => {
                console.log(arguments);
            });
        };

        this.unregister = function() {
            $http.get("/games/unregister").catch(() => {
                console.log(arguments);
            });
        };
    },
    bindings: {
        user: '<'
    }
});