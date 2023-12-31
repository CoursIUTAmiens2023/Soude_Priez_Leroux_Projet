package fr.soudepriezleroux.map;

import java.util.*;

public class MatriceMap {
    private static int[][] matrice;

    // Reminder: x = ordonnée et y = abscisse, (0,0) en haut à gauche, les x vont vers le bas et les y vont vers la droite

    // Initialise la matrice de jeu, chaque entier représente une "case" de ma map
    // 0 = vide, 1 = point, 2 = pacgum, 3 = fruit, 4 = mur, 5 = zone fantôme
    public static void init() {
        matrice = new int[][]{
                {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4},
                {4,1,1,1,1,1,1,1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,1,1,1,4},
                {4,1,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
                {4,2,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,2,4},
                {4,1,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
                {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
                {4,1,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,1,4},
                {4,1,4,4,4,4,1,4,4,1,4,4,4,4,4,4,4,1,4,4,1,4,4,4,4,1,4},
                {4,1,1,1,1,1,1,4,4,1,1,1,1,4,1,1,1,1,4,4,1,1,1,1,1,1,4},
                {4,4,4,4,4,4,1,4,4,4,4,4,0,4,0,4,4,4,4,4,1,4,4,4,4,4,4},
                {0,0,0,0,0,4,1,4,4,4,4,4,0,4,0,4,4,4,4,4,1,4,0,0,0,0,0},
                {0,0,0,0,0,4,1,4,4,0,0,0,0,0,0,0,0,0,4,4,1,4,0,0,0,0,0},
                {0,0,0,0,0,4,1,4,4,0,4,4,4,5,4,4,4,0,4,4,1,4,0,0,0,0,0},
                {4,4,4,4,4,4,1,4,4,0,4,5,5,5,5,5,4,0,4,4,1,4,4,4,4,4,4},
                {4,0,0,0,0,0,1,0,0,0,4,5,5,5,5,5,4,0,0,0,1,0,0,0,0,0,4},
                {4,4,4,4,4,4,1,4,4,0,4,5,5,5,5,5,4,0,4,4,1,4,4,4,4,4,4},
                {0,0,0,0,0,4,1,4,4,0,4,4,4,4,4,4,4,0,4,4,1,4,0,0,0,0,0},
                {0,0,0,0,0,4,1,4,4,0,0,0,0,3,0,0,0,0,4,4,1,4,0,0,0,0,0},
                {0,0,0,0,0,4,1,4,4,0,4,4,4,4,4,4,4,0,4,4,1,4,0,0,0,0,0},
                {4,4,4,4,4,4,1,4,4,0,4,4,4,4,4,4,4,0,4,4,1,4,4,4,4,4,4},
                {4,1,1,1,1,1,1,1,1,1,1,1,1,4,1,1,1,1,1,1,1,1,1,1,1,1,4},
                {4,1,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
                {4,1,4,4,4,4,1,4,4,4,4,4,1,4,1,4,4,4,4,4,1,4,4,4,4,1,4},
                {4,2,1,1,4,4,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,4,4,1,1,2,4},
                {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
                {4,4,4,1,4,4,1,4,4,1,4,4,4,4,4,4,4,1,4,4,1,4,4,1,4,4,4},
                {4,1,1,1,1,1,1,4,4,1,1,1,1,4,1,1,1,1,4,4,1,1,1,1,1,1,4},
                {4,1,4,4,4,4,4,4,4,4,4,4,1,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
                {4,1,4,4,4,4,4,4,4,4,4,4,1,4,1,4,4,4,4,4,4,4,4,4,4,1,4},
                {4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4},
                {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4}
        };

    }

    // Trouve l'itinéraire le plus rapide entre 2 coordonnées de la matrice
    public static List<String> findShortestPath(int startX, int startY, int targetX, int targetY) {
        int rows = matrice.length;
        int cols = matrice[0].length;

        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];

        queue.add(new Node(startX, startY, null));
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.x == targetX && current.y == targetY) {
                // Destination trouvée, créer et retourner le path
                return constructPath(current);
            }

            // Explorer les voisins
            for (Direction dir : Direction.values()) {
                int newX = current.x + dir.dx;
                int newY = current.y + dir.dy;

                if (isValidMove(newX, newY, rows, cols) && matrice[newX][newY] != 4 && matrice[newX][newY] != 5 && !visited[newX][newY]) {
                    queue.add(new Node(newX, newY, current));
                    visited[newX][newY] = true;
                }
            }
        }

        // Si pas de chemin, return liste vide
        return Collections.emptyList();
    }

    // Crée le chemin en remontant la liste des parents
    private static List<String> constructPath(Node destination) {
        List<String> path = new ArrayList<>();

        while (destination.parent != null) {
            path.add(getDirection(destination.parent, destination));
            destination = destination.parent;
        }

        Collections.reverse(path);
        return path;
    }

    // Donne la direction dans laquelle se trouve un node par rapport à un node voisin
    private static String getDirection(Node from, Node to) {
        if (from.x < to.x) return "Bas";
        if (from.x > to.x) return "Haut";
        if (from.y < to.y) return "Droite";
        if (from.y > to.y) return "Gauche";
        return ""; // Sert à rien mais comme ça Java est content :D
    }

    // Vérifie la coordonnée de trouve dans la matrice
    private static boolean isValidMove(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    private static class Node {
        int x, y;
        Node parent;

        public Node(int x, int y, Node parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

    private enum Direction {
        Haut(-1, 0),
        Bas(1, 0),
        Gauche(0, -1),
        Droite(0, 1);

        int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    public static int[][] getMatrice() {
        return matrice;
    }

    public static void setMatrice(int[][] matrice) {
        MatriceMap.matrice = matrice;
    }

    public static void main(String[] args) {
        // Pour faire tes tests avec la map, à enlever dans la version finale
    }

}