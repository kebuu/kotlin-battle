angular.module('app').component('gameView', {
    templateUrl: '/template/gameView.html',
    controller: function($http, $scope, eventLogService) {
        this.game = {};
        this.history = [];
        this.historyStep = 0;

        this.$onInit = function() {
            $http.get(`/games/active`).then((response) => {
                this.game = response.data;
            });

            this.connect();
        };

        this.onStartPressed = function() {
            $http.get(`/games/start`);
            this.history.length = 0;
        };

        this.onStopPressed = function() {
            $http.get(`/games/stop`);
        };

        this.onResumePressed = function() {
            $http.get(`/games/resume`);
            this.history.length = 0;
            eventLogService.clear();
        };

        this.onHistoryBack = function() {
            if(this.historyStep > 0) {
                this.historyStep -= 1;
                this.game.board = this.history[this.historyStep].board;
            }
        };

        this.onHistoryNext = function() {
            if(this.historyStep < this.history.length - 1) {
                this.historyStep += 1;
                this.game.board = this.history[this.historyStep].board;
            }
        };

        this.connect = function() {
            var socket = new SockJS('/kotlin-battle');
            let stompClient = Stomp.over(socket);
            stompClient.debug = null

            stompClient.connect({}, frame =>  {
                stompClient.subscribe('/topic/active-game', data =>  {
                    $scope.$apply(() => {
                        let newGameState = JSON.parse(data.body);
                        this.game = newGameState;

                        if(this.game.status == 'STARTED') {
                            this.history.push(newGameState);
                            this.historyStep = this.game.currentStep;
                        }
                    })
                });

                stompClient.subscribe('/topic/event', data =>  {
                    $scope.$apply(() => {
                        let eventLog = JSON.parse(data.body);
                        eventLogService.addLog({
                            timestamp: eventLog.timestamp,
                            username: eventLog.username,
                            message: (eventLog.gameStep + 1) + '. ' + eventLog.message
                        });
                    })
                });
            });
        };

        this.isUserAdmin = function() {
            return this.user.authorities.some(authority => authority.authority == "ROLE_ADMIN");
        };

        this.isUserRegistered = function() {
            return this.game.gamers.some(gamer => gamer.gamerId == this.user.email);
        };

        this.register = function() {
            $http.get("/games/register");
        };

        this.unregister = function() {
            $http.get("/games/unregister");
        };
    },
    bindings: {
        user: '<'
    }
});