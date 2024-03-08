

public class CartManager {
    public static void addToCart(String productId, Product product) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference cartItemRef = FirebaseDatabase.getInstance().getReference().child("carts").child(userId).child(productId);

            cartItemRef.setValue(product)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Handle successful addition to cart
                        } else {
                            // Handle failure to add to cart
                        }
                    });
        } else {
            // User not authenticated, handle accordingly
        }
    }

}