const app = angular.module("myapp", []);

app.run(function($rootScope, $http) {
    $http.get(`/rest/user`).then(resp => {
        if (resp.data) {
            $rootScope.$u = resp.data;
        }
    });

    $http.get(`/rest/pay`).then(resp => {
        if (resp.data) {
            $rootScope.$pay = resp.data;
            console.log($rootScope.$pay);
        }
    });
})

app.controller("mycontroller", function($scope, $http, $rootScope) {

    // $scope.user = {
    //     getAccount() {
    //         $http.get(`/rest/user`).then(resp => {
    //             if (resp.data) {
    //                 $rootScope.$u = resp.data;

    //                 console.log($rootScope.$u);
    //             }
    //         });
    //     }
    // };

});