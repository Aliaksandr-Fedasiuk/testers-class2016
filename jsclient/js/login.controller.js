(function () {
    'use strict';

    angular
        .module('app')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$location', 'AuthenticationService', 'FlashService'];
    function LoginController($location, AuthenticationService, FlashService) {
        var vm = this;
        vm.login = login;

        (function initController() {
            AuthenticationService.ClearCredentials();
        })();

        function login() {
            vm.dataLoading = true;
            AuthenticationService.Login(vm.username, vm.password, function (data, status, header) {
                if (status == 200) {
                    AuthenticationService.SetCredentials(data, header);
                    $location.path('/');
                } else {
                    FlashService.Error(data, false);
                    vm.dataLoading = false;
                }
            });
        };
    }
})();