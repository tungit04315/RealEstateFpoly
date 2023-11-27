const app = angular.module("myapp", []);
//aaaa
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
            console.log(resp.data);
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

    $http.get(`/rest/province`).then(resp => {
        if (resp.data) {
            $rootScope.provinces = resp.data;
            console.log($rootScope.provinces);
        }
    });

    $http.get(`/rest/service-pack`).then(resp => {
        if (resp.data) {
            $rootScope.pack = resp.data;

            console.log($rootScope.pack[0].services_id === 1);

        }
    });
    $rootScope.checked = function(id) {
        return $rootScope.pack[0].services_id === id;
    }

});

app.controller("mycontroller", function($scope, $http, $rootScope) {

    $scope.selectedProvince = '';
    $scope.selectedDistrict = '';
    $scope.selectedWard = '';
    $scope.street = '';
    $scope.inputNumber = 0;

    $scope.loadDistricts = function() {
        $http.get(`/rest/district?id=` + $scope.selectedProvince)
            .then(resp => {
                if (resp.data) {
                    $rootScope.districts = resp.data;
                    console.log($rootScope.districts);
                }
            });
    };

    $scope.loadWards = function() {
        $http.get(`/rest/wards?id=` + $scope.selectedDistrict)
            .then(function(response) {
                $scope.wards = response.data;
            });
    };

    $scope.updateAddress = function() {
        var provinceName = $scope.provinces.find(function(province) {
            var number = parseInt($scope.selectedProvince, 10);

            var provinceIdType = typeof province.province_id;
            var selectedProvinceType = typeof $scope.selectedProvince;

            // console.log("Kiểu dữ liệu của province.province_id: " + provinceIdType);
            // console.log("Kiểu dữ liệu của $scope.selectedProvince: " + selectedProvinceType);

            // console.log(province.province_id + ' đầu')
            // console.log(number + ' đầu1')

            // console.log(province.province_id === number)
            if (province.province_id === number) {
                return province.name;
            }
            return null;
        });
        var districtName = $scope.districts.find(function(district) {
            var number = parseInt($scope.selectedDistrict, 10);
            if (district.district_id === number) {
                return district.name;
            }
            return null;
        });
        var wardName = $scope.wards.find(function(ward) {
            var number = parseInt($scope.selectedWard, 10);
            if (ward.wards_id === number) {
                return ward.name;
            }
            return null;
        });

        if ($scope.street.length == 0) {
            $scope.addresss = wardName.name + ', ' + districtName.name + ', ' + provinceName.name;
        } else {
            $scope.addresss = $scope.street + ', ' + wardName.name + ', ' + districtName.name + ', ' + provinceName.name;
        }

    };

    $http.get(`/rest/province`)
        .then(function(response) {
            $scope.provinces = response.data;
        });

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
                        post_id: $scope.post,
                        users: $rootScope.$u
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


    $scope.bed = 1;

    $scope.increaseBedroomCount = function() {
        if ($scope.bed < 100) {
            $scope.bed++;
        }
    };

    $scope.decreaseBedroomCount = function() {
        if ($scope.bed > 1) {
            $scope.bed--;
        }
    };

    $scope.toilet = 1;

    $scope.increaseToiletCount = function() {
        if ($scope.toilet < 100) {
            $scope.toilet++;
        }
    };

    $scope.decreaseToiletCount = function() {
        if ($scope.toilet > 1) {
            $scope.toilet--;
        }
    };

    function endDate() {
        $scope.currentDate = new Date();
        //var nthDay = parseInt($scope.end_date, 10);
        //console.log($scope.inputNumber);
        if (!isNaN($scope.inputNumber) && $scope.inputNumber >= 0) {
            var currentDate = new Date($scope.currentDate);
            currentDate.setDate(currentDate.getDate() + $scope.inputNumber);
            return $scope.inputNumber = currentDate;
        } else {
            return $scope.inputNumber = "Số ngày không hợp lệ.";
        }
    }

    $scope.getService = function(id) {
        $http.get(`/rest/service-pack-findById?id=` + id).then(resp => {
            if (resp.data) {
                console.log(id);
                $scope.service = resp.data;
            }
        });
    }

    $scope.searchType = function() {
        $http.get(`/type-property-findById?id=` + $scope.types_id).then(resp => {
            if (resp.data) {
                $rootScope.type = resp.data;
                console.log($rootScope.type);
            }
        });
    }


    // Create Post
    $scope.createPost = function() {
        console.log($scope.service);
        $http.get(`/rest/user`).then(resp => {
            if (resp.data) {
                $rootScope.$u = resp.data;
                console.log(resp.data);
            }
        });
        console.log($rootScope.type);
        //$scope.type = { "types_id": 1, "types_name": "Nhà riêng" };
        var post = {
            post_title: $scope.post_title,
            post_content: $scope.post_content,
            create_at: new Date(),
            end_date: endDate(),
            acreage: $scope.acreage,
            price: $scope.price,
            addresss: $scope.addresss,
            linkVideo: $scope.linkVideo,
            services_id: $scope.service,
            types_id: $scope.type,
            direction: $scope.direction,
            bed: $scope.bed,
            juridical: $scope.juridical,
            balcony: $scope.balcony,
            toilet: $scope.toilet,
            interior: $scope.interior,
            active: true,
            users_id: $rootScope.$u,
            deletedAt: false
        };

        $http.post(`/create-post`, post)
            .then(function(response) {
                // Xử lý thành công
                swal("Good job!", "Đăng bài thành công!", "success");
            }, function(error) {
                // Xử lý lỗi
                alert('Đã có lỗi xảy ra: ' + error.data.message);
                swal("Error!", "Đăng bài thất bại!", "error");
            });
    }
});


//java script goong map
goongjs.accessToken = 'GsUEtexN59WDYJ8cfpBllo4zFhQU17QbU1yGYNx2';

let checkC = 0;
var marker;
var map = new goongjs.Map({
    container: 'map',
    style: 'https://tiles.goong.io/assets/goong_map_web.json',
    center: [105.74241504658403, 10.060186701320404],
    zoom: 13
});

// Add the control to the map.
map.addControl(
    new GoongGeocoder({
        accessToken: 'HjqHdYaa2gKzvL9CZO903kwifZjFrj1cGPcTWdus',
        goongjs: goongjs,
        marker: false,
        placeholder: "Nhập địa chỉ vào đây...",
    })
);
map.addControl(
    new goongjs.GeolocateControl({
        positionOptions: {
            //enableHighAccuracy: true
            timeout: 1000
        },
        trackUserLocation: true,
        showUserLocation: true
    })
);

map.on('click', function(e) {
    if (checkC == 1) {
        // danh dau marker khi click
        marker = new goongjs.Marker()
            .setLngLat(e.lngLat)
            .addTo(map);

        //map.setCenter(e.lngLat);
        checkC = 0;
        console.log("marker on click:" + marker.getLngLat());
    } else {
        console.log("khong chon");
    }

});

function chooseMarker() {
    checkC = 1;
}
/*  function saveMarker() {
      //luu dia chi lnglat vao db
      console.log("abc");
      checkC = 0;
  }*/
function cancelMarker() {
    marker.remove();
    console.log("cancel ne");
    checkC = 0;
}