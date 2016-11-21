angular.module('app').component('eventLog', {
    templateUrl: '/template/eventLog.html',
    controller: function(eventLogService) {
        var ctrl = this;
        ctrl.loading = false;

        ctrl.$onInit = function() {
            ctrl.logs = eventLogService.getLogs();
        };
    },
    bindings: {

    }
});