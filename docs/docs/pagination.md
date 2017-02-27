---
id: pagination
title: Pagination
permalink: docs/pagination.html
prev: default-routes.html
next: relationships.html
---

## Pagination

Let's say we have defined a `messages` resource.

```
resource messages
  text string indexed
  createdAt datetime required
```

There are default query parameters that can be used on the resources default routes. To paginate over a list of documents for example, use the `page` and `limit` query parameters.

```
GET http://localhost:8080/messages?page=2&limit=5
```

This will retrieve 5 documents on the second page of results.

> Note: If you only pass the `page` query parameter, `limit` will be set to 10 by default.

### Filtering

Sometimes we may only need certain fields of documents. To save bandwidth, we can pass the `fields` query parameter, which will only return the specified fields.

```
GET http://localhost:8080/messages?page=2&limit=5&fields=text
```

This will retrieve 5 documents on the second page of results, and only return the `text` field as `JSON` in the body.

### Sorting

To sort a list of results, use the `sort` with the `order` query parameters. The `order` query parameter can take either `asc` or `desc`.

```
GET http://localhost:8080/messages?sort=text&order=asc
```

This will sort the result list by the `text` field in ascending order.

> Note: If you only pass the `sort` query parameter, `order` will be set to ascending by default.