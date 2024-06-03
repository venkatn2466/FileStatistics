# Text File Statistics Application

## Overview

This application allows users to upload a text file and view various statistics about the text, including:
- Number of words
- Number of letters
- Number of symbols
- Top three most common words
- Top three most common letters

## How to Run the Project

### Prerequisites

- Java 11 or higher
- Maven

### Steps to Run

1. Clone the repository:
    ```sh
    git clone https://github.com/venkatn2466/FileStatistics.git
    cd FileStatisitcs
    This is backend code - You run maven commands to build it and spring boot is run on default localHost:8080 server that can be changed in application.properties for port if port conflict.
    Once server is Up, can access Open Api UI(Swagger 3.0) in
    # Swagger url :http://localhost:8080/swagger-ui/index.html
     Can view APi Project Documentation on API's and also run from this URL.
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

4.  Running UI Steps:
     - In Home Page of Repo
     - cd UiFileStats
     - Open this folder in visual Studio for Ease of code and check and building
     - Install Live Server Extension in Visual Studio
     - Be Present in index.html file
     - Launch live server from icon at boom left of window
    Browser open Automatically running at 5500 Port like this
    # Url : http://127.0.0.1:5500/src/index.html
    Alternatively, if nodeJS , npm is installed can run below commands
    # npm i to install liver server dependency and then run npm start command
Open `index.html` in your browser to access the front-end and upload a text file.

## Design Choices

- **Spring Boot**: For the backend to handle file uploads and process text statistics.
- **MultipartFile**: To handle file uploads.
- **BufferedReader**: To efficiently read large text files.
- **Java Streams**: To process and compute statistics from the text content.
- **HTML/JavaScript**: For a simple and quick front-end to demonstrate the functionality.

## Error Handling

The application handles:
- Invalid file formats (only `.txt` files are accepted), File Size and also Empty Files
- File processing errors.

## Testing

Unit tests are provided for the service layer, Repository layer to ensure the correctness of the text statistics computation.

## Future Improvements

- Enhance the front-end with a more modern framework (e.g., React).
- Improve performance for even larger files.
- Add more detailed error messages and handling for various edge cases.
- Add Login ,Authentication and Authorization, caching etch mechanisms to make it as full fledged project
