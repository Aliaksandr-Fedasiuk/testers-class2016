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
            AuthenticationService.Login(vm.username, vm.password, function (username, status, header) {
                if (status == 200) {
                    AuthenticationService.SetCredentials(username, header);
                    $location.path('/');
                } else {
                    if (status == 401) {
                        FlashService.Error("Unauthorized: Access is denied due to invalid credentials.", false);
                    } else {
                        FlashService.Error("Request error: " + status, false);
                    }
                    vm.dataLoading = false;
                }
            });
        };
    }
})();