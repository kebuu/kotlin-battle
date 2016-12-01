angular.module('app').component('mainView', {
    templateUrl: '/template/mainView.html',
    controller: function($http, $scope) {
        this.loading = true;
        this.loggedUser = null;

        this.$onInit = function() {
            $http.get(`/users/logged`).then((response) => {
                this.loggedUser = response.data;
            })
            .catch(() => {
                this.loggedUser = null;
            })
            .finally(() => {
                this.loading = false;
            });
        };

        this.isUserLogged = function() {
            return this.loggedUser !== null
        };
    },
    bindings: {

    }
});