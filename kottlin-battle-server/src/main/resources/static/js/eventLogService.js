angular.module('app').factory('eventLogService', function() {
    let logs = [];

    let api = {
        addLog: function(message) {
            logs.push(message);
        },

        getLogs: function() {
            return logs;
        }
    };

    return api;
});