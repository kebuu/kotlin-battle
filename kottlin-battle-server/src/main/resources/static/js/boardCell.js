angular.module('app').component('boardCell', {
    templateUrl: '/template/boardCell.html',
    controller: function(constants) {
        let cellItems = [];

        this.$onInit = function() {
            this.getCellItems(this);
        };

        this.$onChanges = function() {
            this.getCellItems(this);
        };

        this.hasMountain = function(){
            return this.hasType(constants.MOUNTAIN_TYPE);
        };

        this.hasHole = function(){
            return this.hasType(constants.HOLE_TYPE);
        };

        this.hasTreasure = function(){
            return this.hasType(constants.TREASURE_TYPE);
        };

        this.getClass = function(){
            let cellClass = "cell";

            if(this.hasType(constants.TREASURE_TYPE)) {
                cellClass += " " + constants.TREASURE_TYPE;
            } else if(this.hasType(constants.HOLE_TYPE)) {
                cellClass += " " + constants.HOLE_TYPE;
            } else if(this.hasType(constants.MOUNTAIN_TYPE)) {
                cellClass += " " + constants.MOUNTAIN_TYPE;
            }

            return cellClass;
        };

        this.getSpawns = function(){
            return cellItems.filter(el => {
                return el.type == constants.SPAWN_TYPE;
            });
        }

        this.hasType = function(type) {
            return cellItems.findIndex(el => {
                return el.type == type;
            }) !== -1;
        };

        this.rows = function() {
            return new Array(this.model.dimension.y);
        };

        this.columns = function() {
            return new Array(this.model.dimension.x);
        };

        this.getCellItems = function() {
            cellItems = this.cells[this.row + "_" + this.column] || [];
        };
    },
    bindings: {
        cells: '<',
        row: '<',
        column: '<',
    }
});