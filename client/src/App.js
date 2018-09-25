import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import FileUpload from './components/FileUpload';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">File Upload</h1>
        </header>
        <div className="App-intro">
          <FileUpload />
        </div>
      </div>
    );
  }
}

export default App;
