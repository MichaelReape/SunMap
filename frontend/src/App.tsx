import { useState } from "react";
import "./App.css";
import MapView from "./MapView";

function App() {
  //stores the entered address
  const [address, setAddress] = useState("");
  //stores the result of the API call
  const [result, setResult] = useState<any>(null);
  //function to handle the search when the button is clicked
  const handleSearch = async () => {
    try {
      //the url using the stored env variable
      const url = `${
        import.meta.env.VITE_API_BASE_URL
      }/geocode?address=${encodeURIComponent(address)}`;
      console.log(url);
      //sending the GET request
      const response = await fetch(url);
      //check if response is ok
      if (!response.ok) {
        throw new Error("Request failed!");
      }
      //parse the json
      const data = await response.json();
      //update the result state
      // setResult(data);
      setResult({ lat: Number(data.lat), lon: Number(data.lon) });
    } catch (error) {
      //need to handle 404 not found at some point
      console.error(error);
      setResult({ error: "An error occurred while fetching the data." });
    }
  };
  return (
    <div style={{ padding: "2rem" }}>
      <h1>SunMap Geocode Test</h1>
      {/* Textbox for the address */}
      <input
        type="text"
        value={address}
        onChange={(e) => setAddress(e.target.value)}
        placeholder="Enter an address"
        style={{ marginRight: "1rem", padding: "0.5rem" }}
      />
      {/* Button to trigger backend call */}
      <button onClick={handleSearch}>Search</button>
      {/* Map view */}
      {result && <MapView lat={result.lat} lon={result.lon} />}
    </div>
  );
}

export default App;
