<!DOCTYPE html>
<html>
  <head>
    <title>Simple Map</title>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
  </head>
  <body>
    <div id="map"></div>
    <script>
      var map;
      function initMap() {
      var directionsService = new google.maps.DirectionsService();
      var directionsDisplay = new google.maps.DirectionsRenderer();

      var unqui = {
        lat: -34.70637,
        lng: -58.2772431
      };
      var addressLucas = {
        lat: -34.783098,
        lng: -58.216737
      };

      var request = {
        origin: addressLucas,
        destination: unqui,
        travelMode: google.maps.TravelMode.DRIVING
      };

      var map = new google.maps.Map(document.getElementById('map'), {
        center: unqui,
        zoom: 11
      });

      var marker = new google.maps.Marker({
        position: unqui,
        map: map,
        title: 'Universidad Nacional de Quilmes'
      });
      var marker = new google.maps.Marker({
        position: addressLucas,
        map: map,
        title: 'Casa de Sandi'
      });

      directionsService.route(request, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
        directionsDisplay.setDirections(response);
        directionsDisplay.setMap(map);
        } else {
        alert("Directions Request from " + start.toUrlValue(6) + " to " + end.toUrlValue(6) + " failed: " + status);
        }
      });

	  }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB1ZXtqtTXdoUYnqe1toQzOGGV_Yw-trM4&callback=initMap"
    async defer></script>
  </body>
</html>

