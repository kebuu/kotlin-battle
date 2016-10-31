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
                    })
                });
            });
        }
    },
    bindings: {

    }
});