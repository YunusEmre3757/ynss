   @"
   # Proje Mimarisi

   ```mermaid
   graph TD
     AppModule(("AppModule"))
     subgraph Features
       HomeModule
       ProductModule
       StoreModule
       CartModule
       CheckoutModule
       FavoritesModule
       OrdersModule
       OrderSuccessModule
       ProfileModule
       AuthModule
       SellerModule
       AdminModule
     end
     subgraph Core
       Guards[authGuard<br/>adminGuard<br/>sellerGuard]
       AuthInterceptor
       SharedModule
     end
     subgraph Services
       AuthService
       ProductService
       CartService
       OrderService
       StoreService
       AdminService
       CategoryService
       BrandService
       PaymentService
       FavoriteService
       ReviewService
       AddressService
       VerificationService
     end
     subgraph Infrastructure
       HttpClientModule
       NgxStripeModule
       environments[(environments.ts)]
     end
     subgraph Assets
       assetsFolder[[/assets/**]]
     end

     %% EDGES
     AppModule --> Features
     AppModule --> Core
     Core --> Services
     Services --> Infrastructure
     Infrastructure --> BackendAPI[(REST API)]
     Assets -. usedBy .-> Features
   ```
   "@ | Out-File -FilePath "AngularP\docs\architecture.md"