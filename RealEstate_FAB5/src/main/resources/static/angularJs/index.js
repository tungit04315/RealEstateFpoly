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
                console.log(like.post_id.post_id);
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

    $http.get(`/type-property`).then(resp => {
        if (resp.data) {
            $rootScope.types = resp.data;
            console.log($rootScope.types);
        }
    });

})

app.controller("mycontroller", function($scope, $http, $rootScope) {

    $scope.toggleLike = function(post_id) {
        $http.get(`/find-by-post-likes`)
            .then(function(response) {
                $rootScope.likes = response.data;
                var found = false;
                $rootScope.likes.forEach(like => {
                    if (like.post_id.post_id === post_id) {
                        found = true;
                        $http.delete(`/likes-delete/${post_id}`)
                            .then(function() {

                            })
                            .catch(function(error) {
                                console.error("Lỗi xóa bài viết khỏi danh sách yêu thích: ", error);
                            });
                    }
                });
                // search post id
                $http.get(`/post-id/${post_id}`).then(function(response) {
                    $scope.post = response.data;
                    console.log($scope.post);
                });

                $http.get(`/rest/user`).then(resp => {
                    if (resp.data) {
                        $rootScope.$u = resp.data;
                        console.log($rootScope.$u);
                    }
                });
                if (!found) {
                    $scope.likes = {
                        likes_status: true,
                        likes_date: new Date(),
                        post_id: {
                            post_id: null,
                            post_title: null,
                            post_content: null,
                            create_at: null,
                            end_date: null,

                            acreage: null,
                            price: null,
                            addresss: null,
                            linkVideo: null,
                            services_id: {
                                services_id: null,
                                services_name: null,
                                services_price: null,
                                services_location: null
                            },
                            types_id: {
                                types_id: null,
                                types_name: null
                            }
                        },
                        users: {
                            username: null,
                            passwords: null,
                            email: null,
                            phone: null,
                            fullname: null,
                            avatar: null,
                            addresss: null,
                            fail_login: null,
                            active: null,
                            gender: null,
                            create_block: null,
                            ranks_id: {

                            },
                            pay_id: {
                                pay_id: null,
                                pay_money: null
                            }
                        }
                    };
                    var like = $scope.likes;
                    $http.post('/likes-add', like)
                        .then(function() {

                        })
                        .catch(function(error) {
                            console.error('Lỗi thêm bài viết vào danh sách yêu thích: ', error);
                        });
                }
            })
            .catch(function(error) {
                console.error("Lỗi truy vấn danh sách yêu thích:", error);
            });
    };

});