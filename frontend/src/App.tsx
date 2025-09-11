import { useState } from "react";
import "./App.css";
import MapView from "./MapView";

function App() {
  //function to handle login / sign up button click
  const [showModal, setShowModal] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const handleLoginSignUpModal = () => {
    try {
      if (showModal) {
        setShowModal(false);
        return;
      } else {
        setShowModal(true);
        return;
      }
    } catch (error) {
      console.log(error);
    }
  };
  const handleLoginSignUp = () => {
    try {
      //have to do some sanitization here, valid email, password format (caps, numbers, special, length)
      console.log(email);
      console.log(password);
    } catch (error) {
      console.log(error);
    }
  };

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
    <div>
      {/* login / sign up button */}
      <div style={{ position: "absolute", top: 10, right: 10 }}>
        <button
          className="buttonStyle"
          onClick={handleLoginSignUpModal}
          style={{ marginRight: "1rem" }}
        >
          Login/Sign up
        </button>
      </div>
      {/* <modal></modal> Need to work on this later to appear over the page instead of just at the top*/}
      {showModal && (
        <div id="loginSignupModal">
          <div className="loginModalContent">
            <h2>Login or Sign up</h2>
            <input
              type="text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Email"
            />
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Password"
            />
            {/* how to add style from .css to button? */}
            <button className="buttonStyle" onClick={handleLoginSignUp}>
              Login
            </button>
            <button className="buttonStyle" onClick={() => setShowModal(false)}>
              X
            </button>
          </div>
        </div>
      )}
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
        <button className="buttonStyle" onClick={handleSearch}>
          Search
        </button>
        {/* Map view */}
        <div style={{ width: "600px", height: "400px", marginTop: "2rem" }}>
          {result && <MapView lat={result.lat} lon={result.lon} />}
        </div>
      </div>
    </div>
  );
}

export default App;
