(function () {
    'use strict';

    angular
        .module('app')
        .controller('AddUserController', AddUserController);

    AddUserController.$inject = ['UserService', '$location', '$rootScope', '$routeParams'];
    function AddUserController(UserService, $location, $rootScope, $routeParams) {
        var vm = this;

        vm.addUser = addUser;

        function addUser() {
            UserService.Create(vm.user);
            $location.path('/');
        };
    }
})();