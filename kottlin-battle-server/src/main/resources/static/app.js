var app = angular.module('app', []);

app.component('board', {
    templateUrl: 'board.html',
    controller: function($timeout, $http) {

        var ctrl = this;
        ctrl.rows = [[]];

        ctrl.$onInit = function() {
            $http.get('/boards/1').then((response) => {
                console.log(response);
                ctrl.rows = response.data.cells;
            })
        }
    },
    bindings: {
        data: '<'
    }
});