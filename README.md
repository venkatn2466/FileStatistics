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
    git clone https://github.com/FileStatistic/textfilestats.git
    cd textfilestats
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

4. Open `index.html` in your browser to access the front-end and upload a text file.

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
