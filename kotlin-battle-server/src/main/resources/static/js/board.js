angular.module('app').component('board', {
    templateUrl: '/template/board.html',
    controller: function() {

        this.rows = function() {
            return new Array(this.model.dimension.y);
        };

        this.columns = function() {
            return new Array(this.model.dimension.x);
        };
    },
    bindings: {
        model: '<'
    }
});