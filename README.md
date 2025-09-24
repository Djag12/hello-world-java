Destin Gilbert

Assignment 2 – ETL Pipeline in Java

Overview
For this assignment I built a small ETL program in plain Java. The program takes in a CSV file called `products.csv`, transforms the data based on the rules from class, and then saves the results in a new file called `transformed_products.csv`.

 What the program does
1. Changes all product names to uppercase  
2. Applies a 10% discount if the product is in the Electronics category (rounded to 2 decimals)  
3. If the discounted Electronics price is more than $500, it changes the category to “Premium Electronics”  
4. Adds a new column called `PriceRange` with values Low, Medium, High, or Premium  

The program also handles edge cases like missing or empty files and prints a little summary at the end.



How to Run
From the project root:
```powershell
javac -d out -sourcepath src src/org/howard/edu/lsp/assignment2/ETLPipeline.java
java -cp out org.howard.edu.lsp.assignment2.ETLPipeline

I used ChatGPT to help me through the process. It was useful when I ran into errors I didn’t fully understand, like the BOM (encoding) problem at the top of my Java file. The AI explained what was wrong and how to fix it, which helped me actually learn more about Java setup and file encoding as im still learning java more, i used it as well to make sure my readme is oraganized and makes sense i did write it myself however.
