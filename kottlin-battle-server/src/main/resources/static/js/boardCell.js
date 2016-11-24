angular.module('app').component('boardCell', {
    templateUrl: '/template/boardCell.html',
    controller: function(constants) {
        let cellItems = [];

        this.containsOnlyOneRemoteGamer = null;

        this.$onInit = function() {
            this.prepare();
        };

        this.$onChanges = function() {
            this.prepare();
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

        this.getBackgroundCellClass = function(){
            let cellClass = "";

            if(this.hasType(constants.TREASURE_TYPE)) {
                cellClass = constants.TREASURE_TYPE;
            } else if(this.hasType(constants.HOLE_TYPE)) {
                cellClass = constants.HOLE_TYPE;
            } else if(this.hasType(constants.MOUNTAIN_TYPE)) {
                cellClass = constants.MOUNTAIN_TYPE;
            }

            return cellClass;
        };

        this.getForegroundCellClass = function(){
            let spawns = this.getSpawns();

            if(spawns.length > 1) {
                return 'crowd';
            } else if (spawns.length === 1) {
                return spawns[0].gamerType;
            }
        };

        this.getSpawns = function(){
            return cellItems.filter(el => {
                return el.type == constants.SPAWN_TYPE;
            });
        };

        this.cellTitle = function(){
            return cellItems.filter(el => {
                return el.type == constants.SPAWN_TYPE;
            }).map(spawn => {
                return spawn.gamerShortName
            }).join(",");
        };

        this.hasType = function(type) {
            return cellItems.findIndex(el => {
                return el.type == type;
            }) !== -1;
        };

        this.prepare = function() {
            cellItems = this.cells[this.column + "_" + this.row] || [];

            let spawns = this.getSpawns();
            this.containsOnlyOneRemoteGamer = spawns.length == 1 && spawns[0].gamerType == 'remote';
        };
    },
    bindings: {
        cells: '<',
        row: '<',
        column: '<',
    }
});