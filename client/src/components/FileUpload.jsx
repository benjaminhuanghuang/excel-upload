import React from 'react';

class FileUpload extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      message: '',
    };
  }

  handleUploadFile = (ev) => {
    ev.preventDefault();

    const data = new FormData();
    data.append('file', this.uploadInput.files[0]);
    //data.append('filename', this.fileName.value);  //Can give a desired file name 

    fetch('http://localhost:8888/file', {
      method: 'POST',
      body: data,
    }).then((response) => {
      response.json().then((body) => {
        console.log(body);
        this.setState({ message: body });
      });
    }, (err)=>{
      console.log(err);
    });
  }

  render() {
    return (
      <form onSubmit={this.handleUploadFile}>
        <div>
          <input ref={(ref) => { this.uploadInput = ref; }} type="file" />
        </div>
        <br />
        <div>
          <button>Upload</button>
        </div>
        <div>{this.state.message}</div>
      </form>
    );
  }
}

export default FileUpload;