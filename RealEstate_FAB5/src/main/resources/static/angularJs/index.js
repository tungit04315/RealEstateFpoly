const app = angular.module("myapp", []);

app.run(function($rootScope, $http) {
    const MAX_TITLE_LENGTH = 50;

    // Hàm xử lý tiêu đề
    function handleTitle(title) {
        if (title.length > MAX_TITLE_LENGTH) {
            return title.substr(0, MAX_TITLE_LENGTH) + "...";
        } else {
            return title;
        }
    }

    // Hàm tính thời gian lưu bài viết
    function getDaysSinceSave(saveTime) {
        const now = new Date();

        const diff = now - new Date(saveTime);
        console.log(diff);
        return Math.floor(diff / (1000 * 60 * 60 * 24));
    }


    $http.get(`/likes`).then(response => {
        if (response.data) {
            $rootScope.likes = response.data;

            $rootScope.likes.forEach(like => {
                like.post_id.post_title = handleTitle(like.post_id.post_title);
                like.likes_date = getDaysSinceSave(like.likes_date);
                console.log(like.likes_date);
            });
        }


    });

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