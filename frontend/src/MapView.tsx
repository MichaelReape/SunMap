import { useEffect } from "react";
//imports for leaflet
import { MapContainer, TileLayer, Marker, useMap } from "react-leaflet";

//props
type MapViewProps = {
  //left optional so the map can render without a search, may change later
  lat?: number;
  lon?: number;
};

export default function MapView({ lat, lon }: MapViewProps) {
  return (
    <MapContainer
      center={[lat, lon]}
      zoom={20}
      style={{ height: "100vh", width: "100%" }}
    >
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      {lat && lon && <Marker position={[lat, lon]} />}
      <ChangeView lat={lat} lon={lon} />
    </MapContainer>
  );
}
//component to change the view of the map when the props change
function ChangeView({ lat, lon }: MapViewProps) {
  const map = useMap();

  useEffect(() => {
    if (lat && lon) {
      map.setView([lat, lon]);
    }
  }, [lat, lon, map]);

  return null;
}
