alert("lk souce thanh cong");
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
	                goongjs: goongjs
	            })
	        );
	        map.addControl(
	            new goongjs.GeolocateControl({
	                positionOptions: {
	                    enableHighAccuracy: true
	                },
	                trackUserLocation: true,
	                // showUserLocation: true
	            })
	        );
	
	        map.on('click', function(e) {
	            if (checkC == 1) {
	                document.getElementById('info').innerHTML =
	                    // e.point is the x, y coordinates of the mousemove event relative
	                    // to the top-left corner of the map
	                    // JSON.stringify(e.point) +
	                    // '<br />' +
	                    // e.lngLat is the longitude, latitude geographical position of the event
	                    JSON.stringify(e.lngLat.wrap());
	
	                // danh dau marker khi click
	                marker = new goongjs.Marker()
	                    .setLngLat(e.lngLat)
	                    .addTo(map);
	
	                map.setCenter(e.lngLat);
	                checkC = 0;
	
	            } else {
	                console.log("khong chon");
	            }
	
	        });
	
	        // map.on('dbclick', function(e) {
	//             console.log("aaaa");
	        // });
	
	        function chooseMarker() {
	            checkC = 1;
	        }
	
	        function saveMarker() {
	            //luu dia chi lnglat vao db
	            console.log("abc:" + marker.getOffset());
	            checkC = 0;
	        }
	
	        function cancelMarker() {
	            marker.remove();
	            console.log("cancel ne");
	            checkC = 0;
	        }