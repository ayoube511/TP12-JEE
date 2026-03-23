package ma.ens.security.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TestController {

    /**
     * GET /api/user/profile
     * Accessible aux rôles USER et ADMIN avec un token JWT valide.
     */
    @GetMapping("/api/user/profile")
    public ResponseEntity<?> userProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "message", "✅ Espace utilisateur — accès autorisé",
                "username", auth.getName(),
                "roles", auth.getAuthorities().toString(),
                "type", "USER / ADMIN"
        ));
    }

    /**
     * GET /api/admin/dashboard
     * Accessible uniquement au rôle ADMIN avec un token JWT valide.
     */
    @GetMapping("/api/admin/dashboard")
    public ResponseEntity<?> adminDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "message", "✅ Espace administrateur — accès autorisé",
                "username", auth.getName(),
                "roles", auth.getAuthorities().toString(),
                "type", "ADMIN uniquement"
        ));
    }

    /**
     * GET /api/public
     * Route publique sans authentification.
     */
    @GetMapping("/api/auth/public")
    public ResponseEntity<?> publicEndpoint() {
        return ResponseEntity.ok(Map.of(
                "message", "🌐 Route publique — accessible sans token",
                "status", "OK"
        ));
    }
}
