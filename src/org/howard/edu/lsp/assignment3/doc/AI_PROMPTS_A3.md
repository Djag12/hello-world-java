# AI Prompts (Assignment 3)

**Prompt 1 (design):**  
Refactor my A2 ETL into an OOP design that keeps identical output. What classes and responsibilities should I use?

**Excerpt:**  
Use a Product model, a ProductTransformer for rules, and an ETLPipeline that handles extract/load and composes everything. Keep BigDecimal with HALF_UP for money.

**Prompt 2 (tooling/encoding):**  
javac not recognized / BOM compile error on Windows — how do I fix it?

**Excerpt:**  
Install JDK and add to PATH. Save files without BOM (UTF-8) or use ASCII when creating from PowerShell.

**Prompt 3 (rounding):**  
What is the correct way to round to two decimals?

**Excerpt:**  
BigDecimal.setScale(2, RoundingMode.HALF_UP)

**How I applied it:**  
Followed the suggested class split, used BigDecimal + HALF_UP, fixed encoding, and confirmed A3 output matches A2.
