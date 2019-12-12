# Info on File IO

`StringBuffer` is safe for multithreaded environment; use this for appending lines read from `MultipartFile`

Hm... If we have _keyword pairs_ that significantly ups the complexity of our "matching" logic. So probably best to stick to _single keywords_ for this first implementation.

## MultipartFile?  
--> can we use just File? no, this didn't work
we are using `org.springframework.web.multipart.MultipartFile`
whose details are at :
https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html

it seems best to...
1. start from `@RequestParam("file") MultipartFile file`
2. check if file is empty, using .isEmpty() boolean method
3. call .getInputStream() to receive an InputStream to read the contents of the file from... this can then be wrapped up in an input stream reader for easier processing...
4. you want to be sure to call .close() on this input stream after you're done with it... or use a try-with-resources block...

## InputStreamReader

after you have an InputStream, you might use an InputStreamReader to help bridge the gap from bytes to chars.
see https://docs.oracle.com/en/java/javase/13/docs/api/java.base/java/io/InputStreamReader.html

You can initialize a new InputStreamReader with four possible constructors, the simplest being just `InputStreamReader​(InputStream in)`.

they recommend doing it this way:
```
BufferedReader in
  = new BufferedReader(new InputStreamReader(System.in));
```
where the System.in can be replaced by file.getInputStream(), as that's the `InputStream in` part of it.

## BufferedReader

Finally, BufferedReader is something that gives some useful handles, namely the `.readLine()` method, which returns a string.

### sidebar: .lines() returns Stream
BufferedReader has a `.lines()` which returns a `Stream<String>` on which you can then call a `.distinct()` method, which returns another Stream<String> of all the **distinct** elements in that Stream. That is really cool, because it neatly summarizes the resume into a single group of distinct Strings.

BUT, to use this approach, you would need to first create the list/array_list of individual strings, meaning `.split()` the resume on spaces, and then Stream-ify it, call .distinct().

`.distinct()`, however, is "stateful", meaning it returns another Stream for you to "do more stuff" on... So, you could, at that point, start checking it for matches against the keywords / keyword pairs.

## Closing BufferedReader

If you remember to create & use the BufferedReader in a try-with-resources statement, it will automatically close the BufferedReader for you!...

try (BufferedReader reader =
       new BufferedReader(new FileReader("src/main/resources/input.txt"))) {
    return readAllLines(reader);
}

## from Baeldung, re: SpringBoot configuration

If we want to control the maximum file upload size, we can edit our link.properties:
spring.servlet.multipart.max-file-size=128KB
spring.servlet.multipart.max-request-size=128KB

We can also control whether file uploading is enabled, and the location for file upload:
spring.servlet.multipart.enabled=true
spring.servlet.multipart.location=${java.io.tmpdir}
Note that we've used ${java.io.tmpdir} to define the upload location so that we can use the temporary location for different operating systems.
