(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['UserService', '$location', 'FlashService'];
    function RegisterController(UserService, $location, FlashService) {
        var vm = this;

        vm.register = register;

        function register() {
            vm.dataLoading = true;
            UserService.Create(vm.user, function (data, status, header) {
                if (status == 200) {
                    FlashService.Success('Registration successful.', true);
                    $location.path('/login');
                } else {
                    FlashService.Error(data, false);
                    vm.dataLoading = false;
                }
            });
        }
    }

})();