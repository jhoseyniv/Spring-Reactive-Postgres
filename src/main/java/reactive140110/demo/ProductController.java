package reactive140110.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("all")
    public Flux<Product> getAll(){
        return this.productService.getAllProducts();
    }

    @GetMapping("{productId}")
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable int productId){
        return this.productService.getProductById(productId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Mono<Product> productMono){
        return productMono.flatMap(this.productService::createProduct);
    }

    @PutMapping("{productId}")
    public Mono<Product> updateProduct(@PathVariable int productId,
                                       @RequestBody Mono<Product> productMono){
        return this.productService.updateProduct(productId, productMono);
    }

    @DeleteMapping("{productId}")
    public Mono<Void> deleteProduct(@PathVariable int productId){
        return this.productService.deleteProduct(productId);
    }

}