# Reflection (A2 vs A3)

**What changed**  
A2 was one procedural file. A3 splits the work into classes with clear roles:
- Product is the data model  
- ProductTransformer holds all transformation rules  
- ETLPipeline wires the stages and prints the summary  

**Why A3 is more OO**  
Encapsulation and separation of concerns make the code easier to test and extend. The main class just orchestrates; logic lives in dedicated classes.

**OO ideas used**  
Objects/classes, encapsulation, composition. (Polymorphism could be added later by introducing a Transformer interface and multiple implementations.)

**How I verified behavior equals A2**  
Ran both versions on the same data/products.csv and compared data/transformed_products.csv — they match. Also tested header-only input and missing input cases.
